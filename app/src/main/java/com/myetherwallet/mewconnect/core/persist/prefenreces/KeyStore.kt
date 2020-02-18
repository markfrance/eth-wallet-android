package com.myetherwallet.mewconnect.core.persist.prefenreces

enum class KeyStore(val mnemonic: String, val privateKey: String) {
    PASSWORD("password_encrypted_mnemonic", "password_encrypted_private_key"),
    BIOMETRIC("biometric_encrypted_mnemonic", "biometric_encrypted_private_key")


}