/*
 * Copyright (c) 2021 DuckDuckGo
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.duckduckgo.mobile.android.vpn.ui.report

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import com.duckduckgo.app.global.DuckDuckGoActivity
import com.duckduckgo.mobile.android.ui.viewbinding.viewBinding
import com.duckduckgo.mobile.android.vpn.databinding.ActivityAppTrackersInfoBinding
import com.duckduckgo.mobile.android.vpn.pixels.DeviceShieldPixels
import timber.log.Timber
import javax.inject.Inject

class DeviceShieldAppTrackersInfo : DuckDuckGoActivity() {

    @Inject
    lateinit var deviceShieldPixels: DeviceShieldPixels

    private val binding: ActivityAppTrackersInfoBinding by viewBinding()

    private var timeElapsed: Long = -1
    private var startTime: Long = -1

    override fun onResume() {
        startTime = SystemClock.elapsedRealtime()
        Timber.d("Started Trackers Info screen at $startTime")
        super.onResume()
    }

    override fun onPause() {
        timeElapsed = SystemClock.elapsedRealtime() - startTime
        Timber.d("Spent $timeElapsed ms in the Trackers Info screen")
        super.onPause()
    }

    override fun onDestroy() {
        deviceShieldPixels.didSpendTimeOnTrackersInfoActivity(timeElapsed)
        super.onDestroy()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)
        setupToolbar(binding.includeToolbar.toolbar)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    override fun onBackPressed() {
        finish()
    }

    companion object {

        fun intent(context: Context): Intent {
            return Intent(context, DeviceShieldAppTrackersInfo::class.java)
        }
    }
}
