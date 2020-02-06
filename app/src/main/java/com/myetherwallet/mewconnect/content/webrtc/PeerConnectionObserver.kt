package com.myetherwallet.mewconnect.content.webrtc

import com.myetherwallet.mewconnect.core.utils.MewLog
import org.webrtc.*


private const val TAG = "PeerConnectionObserver"

class PeerConnectionObserver(
        private val onIceConnectionChangeListener: (connectionState: PeerConnection.IceConnectionState) -> Unit,
        private val onIceGatheringStateListener: (connectionState: PeerConnection.IceGatheringState) -> Unit) : PeerConnection.Observer {

    override fun onIceCandidate(iceCandidate: IceCandidate) {
        MewLog.d(TAG, "PeerConnection.onIceCandidate")
    }

    override fun onIceConnectionChange(connectionState: PeerConnection.IceConnectionState) {
        MewLog.d(TAG, "PeerConnection.onIceConnectionChange $connectionState")
        onIceConnectionChangeListener.invoke(connectionState)
    }

    override fun onDataChannel(dataChannel: DataChannel) {
        MewLog.d(TAG, "PeerConnection.onDataChannel")
    }

    override fun onIceConnectionReceivingChange(p0: Boolean) {
        MewLog.d(TAG, "PeerConnection.onIceConnectionReceivingChange")
    }

    override fun onIceGatheringChange(state: PeerConnection.IceGatheringState) {
        MewLog.d(TAG, "PeerConnection.onIceGatheringChange")
        onIceGatheringStateListener.invoke(state)
    }

    override fun onAddStream(p0: MediaStream) {
        MewLog.d(TAG, "PeerConnection.onAddStream")
    }

    override fun onSignalingChange(state: PeerConnection.SignalingState) {
        MewLog.d(TAG, "PeerConnection.onSignalingChange $state")
    }

    override fun onIceCandidatesRemoved(p0: Array<out IceCandidate>) {
        MewLog.d(TAG, "PeerConnection.onIceCandidatesRemoved")
    }

    override fun onRemoveStream(p0: MediaStream) {
        MewLog.d(TAG, "PeerConnection.onRemoveStream")
    }

    override fun onRenegotiationNeeded() {
        MewLog.d(TAG, "PeerConnection.onRenegotiationNeeded")
    }

    override fun onAddTrack(p0: RtpReceiver, p1: Array<out MediaStream>) {
        MewLog.d(TAG, "PeerConnection.onAddTrack")
    }

    override fun onTrack(transceiver: RtpTransceiver?) {
        MewLog.d(TAG, "PeerConnection.onTrack")
    }
}