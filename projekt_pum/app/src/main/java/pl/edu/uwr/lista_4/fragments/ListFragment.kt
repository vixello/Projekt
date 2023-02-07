package pl.edu.uwr.projekt_pum.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import pl.edu.uwr.projekt_pum.databinding.FragmentListBinding
import androidx.recyclerview.widget.RecyclerView
import pl.edu.uwr.projekt_pum.FoodProductsViewModel

class ListFragment: Fragment() {

    private lateinit var binding: FragmentListBinding

    private val itemViewModel: FoodProductsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = ItemAdapter(ItemComparator())
        binding.listRecyclerView.adapter = adapter
        binding.listRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        itemViewModel.readAllData.observe(viewLifecycleOwner, adapter::submitList)
        binding.addItemFAB.setOnClickListener {
            findNavController()
                .navigate(ListFragmentDirections.actionListFragmentToAddFragment())
//            createNotificationChannel()
        }

        binding.clearDataFAB.setOnClickListener { deleteAll()
           }

        swipeToDelete(adapter)
        setupSearchView(adapter)

//        println(notif(current,adapter))
    }

    private fun swipeToDelete(adapter: ItemAdapter) {
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                itemViewModel.deleteItem(adapter.getItemAt(viewHolder.adapterPosition))
            }
        }).attachToRecyclerView(binding.listRecyclerView)
    }

    private fun setupSearchView(adapter: ItemAdapter) {
        binding.searchSearchView
            .setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    if (query != null) search(query, adapter)
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    if (newText != null) search(newText, adapter)
                    return true
                }
            })
    }

    private fun search(query: String, adapter: ItemAdapter) {
        val searchQuery = "%$query%"
        itemViewModel.searchItem(searchQuery)
            .observe(viewLifecycleOwner, adapter::submitList)
    }
//    private fun notif(date: String, adapter: ItemAdapter) {
//        itemViewModel.getDate(date)
//            .observe(viewLifecycleOwner, adapter::submitList)
//
//    }
    private fun deleteAll() {
        itemViewModel.deleteAll()
    }

//    fun getTheDate(date: List<Item>){
//        if(date[0].date == current){
//            println("AAAAAAAAAAAASSSSSSSSSSSSSSSSSSS " + date[0].date)
//
//        }
//
//    }
//    private fun createNotificationChannel() {
//        val descriptionText = "some?.get(0)?.name"
//        val importance = NotificationManager.IMPORTANCE_DEFAULT
////        println("AAAAAAAAAAAA " + (some))
//
//        val channel = NotificationChannel("channel_id", channelName, importance).apply {
//            description = descriptionText
//        }
//
//        val notificationManager: NotificationManager =
//            getActivity()?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//        notificationManager.createNotificationChannel(channel)
//
//        val intent = Intent(requireContext(), ListFragment::class.java).apply {
//            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//        }
//
//        val pendingIntent: PendingIntent =
//            PendingIntent.getActivity(requireContext(), 0, intent, PendingIntent.FLAG_IMMUTABLE)
//
//        val builder = NotificationCompat.Builder(requireContext(), channelID)
//            .setSmallIcon(R.drawable.ic_baseline_notifications_24)
//            .setContentTitle("Powiadomienie")
//            .setContentText("Treść powiadomienia")
//            .setStyle(
//                NotificationCompat.BigTextStyle()
//                    .bigText("Dalszy tekst powiadomienia")
//            )
//            .setContentIntent(pendingIntent)
//            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
//        pushNotificationPermissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
//
//        with(NotificationManagerCompat.from(requireContext())) {
//            notify(notificationId, builder.build())
//        }
//
//    }
//    private fun observeFood(itemAdapter: ItemAdapter) {
//        itemViewModel.foodList.observe(viewLifecycleOwner) {
////            println("ASDDDDDDDDDDDDDDDDD" + it)
//            val some = it
//            getTheDate(some)
//            itemAdapter.submitList(it)
//        }
//    }
}
