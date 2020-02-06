package com.myetherwallet.mewconnect.content.data

import org.webrtc.SessionDescription

data class Offer(
        val type: String?,
        val sdp: String?
) : BaseMessage() {
    constructor(sessionDescription: SessionDescription) : this(sessionDescription.type.canonicalForm(), sessionDescription.description)

    fun toSessionDescription() = SessionDescription(SessionDescription.Type.fromCanonicalForm(type), sdp)
}