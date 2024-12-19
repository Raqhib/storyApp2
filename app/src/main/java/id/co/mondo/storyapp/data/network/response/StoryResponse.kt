package id.co.mondo.storyapp.data.network.response

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class StoryResponse(

    @field:SerializedName("listStory")
	val listStory: List<ListStoryItem> = emptyList(),

    @field:SerializedName("error")
	val error: Boolean? = null,

    @field:SerializedName("message")
	val message: String? = null
)

data class StoryDetailResponse(
	@field:SerializedName("error")
	val error: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("story")
	val story: ListStoryItem? = null
)
@Entity(tableName = "stories")
data class ListStoryItem(

	@PrimaryKey
	@SerializedName("id")
	val id: String,

	@SerializedName("name")
	val name: String? = null,

	@SerializedName("description")
	val description: String? = null,

	@SerializedName("photoUrl")
	val photoUrl: String? = null,

	@SerializedName("createdAt")
	val createdAt: String? = null,

	@SerializedName("lat")
	val lat: Double? = null,

	@SerializedName("lon")
	val lon: Double? = null
)

data class FileUploadResponse(
	@field:SerializedName("error")
	val error: Boolean,
	@field:SerializedName("message")
	val message: String
)