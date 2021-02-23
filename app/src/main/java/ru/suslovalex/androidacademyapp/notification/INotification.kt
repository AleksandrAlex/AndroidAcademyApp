package ru.suslovalex.androidacademyapp.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.net.toUri
import org.koin.core.component.KoinApiExtension
import ru.suslovalex.androidacademyapp.R
import ru.suslovalex.androidacademyapp.data.Movie
import ru.suslovalex.androidacademyapp.retrofit.MoviesApi.Companion.BASE_IMAGE_URL
import ru.suslovalex.androidacademyapp.view.MainActivity
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL


interface INotification{
    fun createChannel()
    fun showNotification(movie: Movie)
    fun dismissNotification(movie: Movie)

}

class MovieNotification(private val context: Context): INotification{

    companion object {
        private const val CHANNEL_NEW_MOVIE = "new_movie"
        private const val REQUEST_CONTENT = 1
        private const val MOVIE_TAG = "movie"
        private const val NAME_NOTIFICATION = "The most rating movie notification"
    }

    private val notificationManagerCompat: NotificationManagerCompat =
        NotificationManagerCompat.from(context)

    override fun createChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channel = NotificationChannel(
                CHANNEL_NEW_MOVIE,
                NAME_NOTIFICATION,
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManagerCompat.createNotificationChannel(channel)
        }
    }

    @KoinApiExtension
    override fun showNotification(movie: Movie) {
        val bitmap = getBitmapFromURL(movie)
        val contentUri = "ru.suslovalex.androidacademyapp://movieapp/${movie.id}".toUri()
        val notification = NotificationCompat.Builder(context, CHANNEL_NEW_MOVIE)
            .setContentTitle(movie.title)
            .setContentText((movie.genres.map { it.name }.joinToString { it }))
            .setSmallIcon(R.drawable.ic_notification_movie)
            .setWhen(System.currentTimeMillis())
//            .setLargeIcon(bitmap)
//            .setStyle(NotificationCompat.BigTextStyle().bigText(movie.overview))
            .setStyle(NotificationCompat.BigPictureStyle().bigPicture(bitmap))
            .setAutoCancel(true)
            .setContentIntent(
                PendingIntent.getActivity(
                    context,
                    REQUEST_CONTENT,
                    Intent(context, MainActivity::class.java)
                        .setAction(Intent.ACTION_VIEW)
                        .setData(contentUri),
                    PendingIntent.FLAG_UPDATE_CURRENT
                )
            )
            .build()

        notificationManagerCompat.notify(MOVIE_TAG, movie.id.toInt(), notification)
        Log.d("NOTIFICATION", "${movie.id}")
    }

    fun getBitmapFromURL(movie: Movie?): Bitmap? {
        return try {
            val url = URL(BASE_IMAGE_URL+movie?.posterPath)
            val connection: HttpURLConnection = url.openConnection() as HttpURLConnection
            connection.setDoInput(true)
            connection.connect()
            val input: InputStream = connection.getInputStream()
            BitmapFactory.decodeStream(input)
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }

    override fun dismissNotification(movie: Movie) {
        notificationManagerCompat.cancel(MOVIE_TAG, movie.id.toInt())
    }
}