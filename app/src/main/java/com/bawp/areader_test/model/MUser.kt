package com.bawp.areader_test.model

import com.google.firebase.firestore.QueryDocumentSnapshot

data class MUser(val id: String?,
                 val userId: String,
                 val displayName: String,
                 val avatarUrl: String,
                 val quote: String,
                 val profession: String){

    fun fromDocument(data: QueryDocumentSnapshot): MUser {
        return MUser(
            id = data.id,
            userId = data.get("user_id") as String,
            displayName = data.get("display_name") as String,
            quote = data.get("quote") as String,
            profession = data.get("profession") as String,
            avatarUrl = data.get("avatar_url") as String
                    )
    }
    fun toMap(): MutableMap<String, Any> {
        return mutableMapOf(
            "user_id" to this.userId,
            "displayName" to this.displayName,
            "quote" to this.quote,
            "profession" to this.profession,
            "avatar_url" to this.avatarUrl)
    }
}