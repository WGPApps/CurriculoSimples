/*
 * Copyright (C) 2018-2021, Wíliam Gonçalves <wgp_apps@hotmail.com>
 *
 * This file is part of Easy Curriculum.
 *
 * Easy Curriculum is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 *
 */

package com.example.curriculosimples;

import android.text.Editable;
import android.text.TextWatcher;

public class MaskWatcher implements TextWatcher {
    private boolean escrevendo = false;
    private boolean removendo = false;
    private final String mask;

    public MaskWatcher(String mask) {
        this.mask = mask;
    }

    @Override
    public void afterTextChanged(Editable editable) {
        if (escrevendo || removendo) {
            return;
        }
        escrevendo = true;

        int editableLength = editable.length();
        if (editableLength < mask.length() && editableLength > 0) {
            if (mask.charAt(editableLength) != '#') {
                editable.append(mask.charAt(editableLength));
            } else if (mask.charAt(editableLength-1) != '#') {
                editable.insert(editableLength-1, mask, editableLength-1, editableLength);
            }
        }

        escrevendo = false;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
        removendo = count > after;
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int start, int before, int count) { }
}