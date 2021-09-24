/*
 * Copyright 2018, The Android Open Source Project
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
 *
 */

package com.example.android.marsrealestate.network

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

// Convert this class to a Kotlin data class that contains properties that match the JSON
// Use @Json to remap the img_src field to imgSrcUrl in the data class
// TODO (12) Add @Parcelize annotation to MarsProperty and have it implement Parcelable
@Parcelize
data class MarsProperty(
    val id:String,
    @Json(name = "img_src")
    val imgSrcUrl: String,
    val type: String,
    val price : Double) : Parcelable {
    // TODO (17) Add isRental Boolean property where get() = type == "rent
    val isRental
    get() = type == "rent"
}

