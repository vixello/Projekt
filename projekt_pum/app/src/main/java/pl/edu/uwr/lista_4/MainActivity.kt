package pl.edu.uwr.projekt_pum

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import pl.edu.uwr.projekt_pum.databinding.ActivityMainBinding
import pl.edu.uwr.projekt_pum.fragments.ItemAdapter
import pl.edu.uwr.projekt_pum.fragments.ItemComparator
import pl.edu.uwr.projekt_pum.model.Item
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class MainActivity : AppCompatActivity() {
    private val channelID = "channel_id"
    private val channelName = "channel_name"
    private val notificationId = 1
    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    val current = LocalDateTime.now().format(formatter)
    private val pushNotificationPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) {
//                granted ->
//            viewModel.inputs.onTurnOnNotificationsClicked(granted)
    }

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private val navController: NavController by lazy {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
                as NavHostFragment
        navHostFragment.findNavController()
    }
    private val appBarConfiguration: AppBarConfiguration by lazy {
        AppBarConfiguration(setOf(R.id.foodProductsFragment, R.id.listFragment))
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        itemViewModel.getDate(current)
        val adapter = ItemAdapter(ItemComparator())
        observeFood(adapter)
        binding.bottomNavView.setupWithNavController(navController)
        setupActionBarWithNavController(navController, appBarConfiguration)
    }


    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
    private val itemViewModel: FoodProductsViewModel by viewModels()


    private fun createNotificationChannel(date: MutableList<String>) {

//        val descriptionn = mutableListOf<String>()
        var descriptionText =""

        for (i in 0..date.size-1)
        {
            descriptionText += date[i] +", "

        }
        descriptionText += " are expiring today "
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel("channel_id", channelName, importance).apply {
            description = descriptionText
        }

        val notificationManager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)

        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent: PendingIntent =
            PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        val builder = NotificationCompat.Builder(this, channelID)
            .setSmallIcon(R.drawable.ic_baseline_notifications_24)
            .setContentTitle("Some products will expire today...")
            .setContentText(descriptionText)
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText(descriptionText + ", remember to eat it/them!")
            )
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        pushNotificationPermissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
        if (date.isNotEmpty()){
            with(NotificationManagerCompat.from(this)) {
                notify(notificationId, builder.build())
            }
        }


    }
    fun getTheDate(dates: List<Item>){
        val date = mutableListOf<String>()
        for (i in 0..dates.size-1)
        {
            if(dates[i].date == current){
                date.add(i,dates[i].name)
//                println("AAAAAAAAAAAASSSSSSSSSSSSSSSSSSS " + date)

            }
        }
        createNotificationChannel(date)

    }

    private fun observeFood(itemAdapter: ItemAdapter) {
        itemViewModel.foodList.observe(this) {
            val some = it
            getTheDate(some)
            itemAdapter.submitList(it)
        }
    }
}