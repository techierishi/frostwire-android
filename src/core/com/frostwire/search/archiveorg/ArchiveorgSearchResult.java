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

package com.frostwire.search.archiveorg;

import com.frostwire.licences.Licence;
import com.frostwire.search.AbstractSearchResult;
import com.frostwire.search.CrawlableSearchResult;
import com.frostwire.search.SearchResult;

/**
 * @author gubatron
 * @author aldenml
 *
 */
public class ArchiveorgSearchResult extends AbstractSearchResult implements CrawlableSearchResult {

    private final ArchiveorgItem item;

    public ArchiveorgSearchResult(ArchiveorgItem item) {
        this.item = item;
    }

    public ArchiveorgItem getItem() {
        return item;
    }

    @Override
    public String getDisplayName() {
        return item.title;
    }

    @Override
    public String getSource() {
        return "Archive.org";
    }

    @Override
    public String getDetailsUrl() {
        return "http://archive.org/details/" + item.identifier;
    }
}
