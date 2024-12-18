package id.co.mondo.storyapp

import id.co.mondo.storyapp.data.network.response.ListStoryItem

object DataDummy {

    fun generateDummyStoryResponse(): List<ListStoryItem> {
        val items: MutableList<ListStoryItem> = arrayListOf()
        for (i in 0..100) {
            val story = ListStoryItem(
                id =  i.toString(),
                name = "user + $i",
                description = "description + $i",
                lat = i.toDouble(),
                lon = i.toDouble(),
                photoUrl = "https://pelatihan-it.fikom.app/wp-content/uploads/2022/03/logo-fikom.png$i",
                createdAt = "18/12/2024"
            )
            items.add(story)
        }
        return items
    }

}