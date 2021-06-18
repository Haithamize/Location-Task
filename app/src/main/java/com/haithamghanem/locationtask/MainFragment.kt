package com.haithamghanem.locationtask

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.work.*
import com.haithamghanem.locationtask.databinding.FragmentMainBinding
import java.util.concurrent.TimeUnit


class MainFragment : Fragment() {

    companion object{
        const val WORKER_DATA = "worker data"
    }


    private lateinit var binding: FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_main, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentMainBinding.bind(view)
        binding.btnGetLocation.setOnClickListener {

            val serviceIntent = Intent(activity,LocationService::class.java)
            activity?.startService(serviceIntent)
            setPeriodicWorkRequest()
        }

        binding.btnStopService.setOnClickListener {
            val serviceIntent = Intent(activity, LocationService::class.java)
            activity?.stopService(serviceIntent)
            WorkManager.getInstance(this.requireContext())
                .cancelAllWorkByTag("periodicWorkRequest")
        }

        binding.goToMap.setOnClickListener {
            it.findNavController().navigate(R.id.action_mainFragment_to_mapFragment)
        }
    }

    private fun setPeriodicWorkRequest() {


        val data: Data = Data.Builder()
            .putBoolean(WORKER_DATA,LocationService.HAS_REACHED_DESTINATION)
            .build()

        val workManager: WorkManager? = this.context?.let { WorkManager.getInstance(it) }
        val periodicWorkRequest = PeriodicWorkRequest.Builder(
            PeriodicWorker::class.java, 1,
            TimeUnit.HOURS
        ).setInputData(data)
            .addTag("periodicWorkRequest")
            .build()

        workManager?.enqueue(periodicWorkRequest)
        workManager?.getWorkInfoByIdLiveData(periodicWorkRequest.id)?.observe(
            viewLifecycleOwner,
            androidx.lifecycle.Observer {
                Log.d("WorkInProgress", "${it.state} ")
            })
    }


}
