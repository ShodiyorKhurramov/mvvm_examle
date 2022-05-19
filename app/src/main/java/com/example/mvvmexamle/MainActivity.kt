package com.example.mvvmexamle

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.mvvmexamle.adapter.UserAdapter
import com.example.mvvmexamle.database.AppDatabase
import com.example.mvvmexamle.databinding.ActivityMainBinding
import com.example.mvvmexamle.network.ApiClient
import com.example.mvvmexamle.repositories.UserRepository
import com.example.mvvmexamle.utils.NetworkHelper
import com.example.mvvmexamle.utils.Status
import com.example.mvvmexamle.viewmodels.UserViewModel
import com.example.mvvmexamle.viewmodels.ViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var userViewModel: UserViewModel

    private lateinit var userAdapter: UserAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val userRepository = UserRepository(ApiClient.apiService, AppDatabase.getInstance(this))
        val networkHelper = NetworkHelper(this)
        userViewModel = ViewModelProvider(
            this,
            ViewModelFactory(userRepository, networkHelper)
        )[UserViewModel::class.java]

        userAdapter = UserAdapter()
        binding.rv.adapter = userAdapter

        binding.progress.visibility = View.VISIBLE
        userViewModel.getUsers().observe(this, Observer {
            when (it.status) {
                Status.LOADING -> {
                    binding.progress.visibility = View.VISIBLE
                    binding.rv.visibility = View.GONE
                }
                Status.ERROR -> {
                    binding.progress.visibility = View.GONE
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                }
                Status.SUCCESS -> {
                    userAdapter.submitList(it.data)
                    binding.progress.visibility = View.GONE
                    binding.rv.visibility = View.VISIBLE
                }
            }
        })
    }
}