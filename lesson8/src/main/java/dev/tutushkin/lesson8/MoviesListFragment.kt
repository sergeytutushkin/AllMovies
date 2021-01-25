package dev.tutushkin.lesson8

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dev.tutushkin.lesson8.adapters.MoviesAdapter
import dev.tutushkin.lesson8.adapters.MoviesListClickListener
import dev.tutushkin.lesson8.viewmodels.MoviesListViewModel
import dev.tutushkin.lesson8.viewmodels.MoviesListViewModelFactory
import kotlinx.serialization.ExperimentalSerializationApi

const val MOVIES_KEY = "MOVIES"

@ExperimentalSerializationApi
class MoviesListFragment : Fragment(R.layout.fragment_movies_list) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        val displayMetrics = DisplayMetrics()
//            ...

        val viewModelFactory = MoviesListViewModelFactory()

        val viewModel = ViewModelProvider(this, viewModelFactory).get(MoviesListViewModel::class.java)

        val recycler = view.findViewById<RecyclerView>(R.id.movies_list_recycler)
        recycler.layoutManager = GridLayoutManager(requireContext(), 2)

        val listener = object : MoviesListClickListener {
            override fun onItemClick(movieId: Int) {
                val bundle = Bundle()
                bundle.putInt(MOVIES_KEY, movieId)
                val detailsFragment = MovieDetailsFragment()
                detailsFragment.arguments = bundle
                requireActivity().supportFragmentManager.beginTransaction()
                    .addToBackStack(null)
                    .replace(R.id.main_container, detailsFragment)
                    .commit()
            }
        }

        val adapter = MoviesAdapter(listener)
        recycler.adapter = adapter

        viewModel.movies.observe(viewLifecycleOwner, {
            adapter.submitList(it)
        })
    }

}