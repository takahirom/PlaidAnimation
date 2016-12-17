/*
 * Copyright 2015 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.takahirom.plaidanimation.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.github.takahirom.plaidanimation.R;
import com.github.takahirom.plaidanimation.transition.FabTransform;


public class LoginActivity extends Activity {

    private static final String STATE_LOGIN_FAILED = "loginFailed";

    boolean isDismissing = false;
    ViewGroup container;
    ProgressBar loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        container = (ViewGroup) findViewById(R.id.container);
        loading = (ProgressBar) findViewById(R.id.loading);
        loading.setVisibility(View.GONE);

        if (!FabTransform.setup(this, container)) {
//            MorphTransform.setup(this, container,
//                    ContextCompat.getColor(this, R.color.background_light),
//                    getResources().getDimensionPixelSize(R.dimen.dialog_corners));
        }
    }


    @Override
    public void onBackPressed() {
        dismiss(null);
    }

    public void dismiss(View view) {
        isDismissing = true;
        setResult(Activity.RESULT_CANCELED);
        finishAfterTransition();
    }

}
