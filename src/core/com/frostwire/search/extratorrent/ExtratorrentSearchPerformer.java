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

package com.frostwire.search.extratorrent;

import java.util.LinkedList;
import java.util.List;

import com.frostwire.search.PagedWebSearchPerformer;
import com.frostwire.search.SearchResult;
import com.frostwire.util.JsonUtils;
import com.frostwire.websearch.TorrentWebSearchResult;

/**
 * @author gubatron
 * @author aldenml
 *
 */
public class ExtratorrentSearchPerformer extends PagedWebSearchPerformer {

    public ExtratorrentSearchPerformer(long token, String keywords, int timeout) {
        super(token, keywords, timeout, 1);
    }

    @Override
    protected String getUrl(int page, String encodedKeywords) {
        return "http://extratorrent.com/json/?search=" + encodedKeywords;
    }

    @Override
    protected List<? extends SearchResult> searchPage(String page) {
        List<SearchResult> result = new LinkedList<SearchResult>();

        ExtratorrentResponse response = JsonUtils.toObject(page, ExtratorrentResponse.class);

        for (ExtratorrentItem item : response.list) {
            if (!isStopped()) {
                TorrentWebSearchResult sr = new ExtratorrentResponseWebSearchResult(item);
                result.add(sr);
            }
        }

        return result;
    }
}
