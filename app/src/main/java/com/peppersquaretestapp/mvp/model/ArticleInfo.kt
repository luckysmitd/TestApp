package com.peppersquaretestapp.mvp.model

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by smit on 29/10/17.
 */

data class ArticleInfo(
		val tags: List<String>?,
		val description: String?, //Article Description
		val id: Int?, //1
		val title: String?, //Article title
		var likes: Int, //0
		val created_at: String?, //2016-06-29T12:27:57.828782+00:00
		val author: String?, //Pepper Square
		val published: Boolean?, //true
		val image: String?, //https://d262ilb51hltx0.cloudfront.net/max/400/1*sxxTVuaXGa0AUdAyYhwwSw.jpeg
		var isLiked: Boolean = false
) : Parcelable {
	constructor(parcel: Parcel) : this(
			parcel.createStringArrayList(),
			parcel.readString(),
			parcel.readValue(Int::class.java.classLoader) as? Int,
			parcel.readString(),
			parcel.readInt(),
			parcel.readString(),
			parcel.readString(),
			parcel.readValue(Boolean::class.java.classLoader) as? Boolean,
			parcel.readString(),
			parcel.readByte() != 0.toByte()) {
	}

	override fun writeToParcel(parcel: Parcel, flags: Int) {
		parcel.writeStringList(tags)
		parcel.writeString(description)
		parcel.writeValue(id)
		parcel.writeString(title)
		parcel.writeInt(likes)
		parcel.writeString(created_at)
		parcel.writeString(author)
		parcel.writeValue(published)
		parcel.writeString(image)
		parcel.writeByte(if (isLiked) 1 else 0)
	}

	override fun describeContents(): Int {
		return 0
	}

	companion object CREATOR : Parcelable.Creator<ArticleInfo> {
		override fun createFromParcel(parcel: Parcel): ArticleInfo {
			return ArticleInfo(parcel)
		}

		override fun newArray(size: Int): Array<ArticleInfo?> {
			return arrayOfNulls(size)
		}
	}
}