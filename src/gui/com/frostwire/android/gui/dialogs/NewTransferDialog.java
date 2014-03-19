/*
 * Created by Angel Leon (@gubatron), Alden Torres (aldenml)
 * Copyright (c) 2011, 2012, FrostWire(TM). All rights reserved.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.frostwire.android.gui.dialogs;

import java.io.Serializable;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.frostwire.android.R;
import com.frostwire.android.core.ConfigurationManager;
import com.frostwire.android.core.Constants;
import com.frostwire.android.gui.util.UIUtils;
import com.frostwire.android.gui.views.AbstractDialog2;
import com.frostwire.android.gui.views.ClickAdapter;
import com.frostwire.search.FileSearchResult;

/**
 * @author gubatron
 * @author aldenml
 *
 */
public class NewTransferDialog extends AbstractDialog2 {

    public static final String TAG = "new_transfer";

    private static final String SEARCH_RESULT_DATA_KEY = "search_result_data";
    private static final String HIDE_CHECK_SHOW_KEY = "hide_check_show";

    private Button buttonNo;
    private Button buttonYes;
    private CheckBox checkShow;

    public NewTransferDialog() {
        super(TAG, R.layout.dialog_new_transfer);
    }

    public static NewTransferDialog newInstance(FileSearchResult sr, boolean hideCheckShow) {
        NewTransferDialog f = new NewTransferDialog();

        Bundle args = new Bundle();
        args.putSerializable(SEARCH_RESULT_DATA_KEY, new SearchResultData(sr));
        args.putBoolean(HIDE_CHECK_SHOW_KEY, hideCheckShow);
        f.setArguments(args);

        return f;
    }

    @Override
    protected void initComponents(Dialog dlg, Bundle savedInstanceState) {
        Bundle args = getArguments();

        SearchResultData sr = (SearchResultData) args.getSerializable(SEARCH_RESULT_DATA_KEY);
        boolean hideCheckShow = args.getBoolean(HIDE_CHECK_SHOW_KEY);

        dlg.setTitle(R.string.dialog_new_transfer_title);

        Context ctx = dlg.getContext();

        String sizeStr = sr.size > 0 ? UIUtils.getBytesInHuman(sr.size) : ctx.getString(R.string.size_unknown);

        TextView textQuestion = findView(dlg, R.id.dialog_new_transfer_text);

        textQuestion.setText(dlg.getContext().getString(R.string.dialog_new_transfer_text_text, sr.displayName, sizeStr));

        buttonYes = findView(dlg, R.id.dialog_new_transfer_button_yes);
        buttonYes.setOnClickListener(new DialogListener(this, true));

        buttonNo = findView(dlg, R.id.dialog_new_transfer_button_no);
        buttonNo.setOnClickListener(new DialogListener(this, false));

        checkShow = findView(dlg, R.id.dialog_new_transfer_check_show);
        checkShow.setChecked(ConfigurationManager.instance().getBoolean(Constants.PREF_KEY_GUI_SHOW_NEW_TRANSFER_DIALOG));
        checkShow.setOnCheckedChangeListener(new DialogListener(this, true));

        if (hideCheckShow) {
            checkShow.setVisibility(View.GONE);
        }
    }

    private static final class DialogListener extends ClickAdapter<NewTransferDialog> {

        private final boolean positive;

        public DialogListener(NewTransferDialog owner, boolean positive) {
            super(owner);
            this.positive = positive;
        }

        @Override
        public void onClick(NewTransferDialog owner, View v) {
            owner.dismiss();
            owner.performDialogClick(positive ? BUTTON_POSITIVE : BUTTON_NEGATIVE);
        }

        @Override
        public void onCheckedChanged(NewTransferDialog owner, CompoundButton buttonView, boolean isChecked) {
            ConfigurationManager.instance().setBoolean(Constants.PREF_KEY_GUI_SHOW_NEW_TRANSFER_DIALOG, isChecked);
        }
    }

    public static final class SearchResultData implements Serializable {

        public SearchResultData(FileSearchResult sr) {
            this.displayName = sr.getDisplayName();
            this.detailsUrl = sr.getDetailsUrl();
            this.creationTime = sr.getCreationTime();
            this.source = sr.getSource();
            this.filename = sr.getFilename();
            this.size = sr.getSize();
        }

        public final String displayName;
        public final String detailsUrl;
        public final long creationTime;
        public final String source;
        public final String filename;
        public final long size;
    }
}
