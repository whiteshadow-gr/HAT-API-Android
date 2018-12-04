package com.hubofallthings.android.hatApi.objects.dataoffers

import com.fasterxml.jackson.annotation.JsonAutoDetect
import com.fasterxml.jackson.annotation.JsonProperty
import java.io.Serializable

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
data class DataOfferRewardsObject(
        // MARK: - Variables

        /// The reward type of the offer
        var rewardType: String = "",
        /// The vendor of the offer
        var vendor: String = "",
        /// The vendor URL
        var vendorUrl: String = "",
        /// The reward value of the offer
        @JsonProperty("value") var rewardValue : String = "",
        /// The reward value of the offer as Int
        var valueInt: Int?,
        /// Is the code of the reward able to be reused
        var areCodesReusable: Boolean?,
        /// The possible codes as rewards
        var codes: Array<String>?,
        /// The cash value of the reward
        var cashValue: DataOfferRewardsCashValueObject?,
        /// The currency of the reward
        var currency: String?
): Serializable