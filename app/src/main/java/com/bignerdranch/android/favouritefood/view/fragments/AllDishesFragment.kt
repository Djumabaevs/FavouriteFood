package com.bignerdranch.android.favouritefood.view.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.bignerdranch.android.favouritefood.R
import com.bignerdranch.android.favouritefood.application.FavDishApplication
import com.bignerdranch.android.favouritefood.databinding.FragmentAllDishesBinding
import com.bignerdranch.android.favouritefood.view.activities.AddUpdateDishActivity
import com.bignerdranch.android.favouritefood.view.adapters.FavDishAdapter
import com.bignerdranch.android.favouritefood.viewmodel.FavDishViewModel
import com.bignerdranch.android.favouritefood.viewmodel.FavDishViewModelFactory
import com.bignerdranch.android.favouritefood.viewmodel.HomeViewModel

class AllDishesFragment : Fragment() {

//    private lateinit var homeViewModel: HomeViewModel

    private lateinit var mBinding: FragmentAllDishesBinding

    private val mFavDishViewModel: FavDishViewModel by viewModels {
        FavDishViewModelFactory((requireActivity().application as FavDishApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        mBinding = FragmentAllDishesBinding.inflate(inflater, container, false)


         /*   homeViewModel =
                ViewModelProvider(this).get(HomeViewModel::class.java)
            val root = inflater.inflate(R.layout.fragment_all_dishes, container, false)
            val textView: TextView = root.findViewById(R.id.text_home)
            homeViewModel.text.observe(viewLifecycleOwner, Observer {
                textView.text = it
            })*/
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBinding.rvDishesList.layoutManager = GridLayoutManager(requireActivity(), 2)
        val favDishAdapter = FavDishAdapter(this@AllDishesFragment)
        mBinding.rvDishesList.adapter = favDishAdapter

        mFavDishViewModel.allDishesList.observe(viewLifecycleOwner) {
            dishes ->
            dishes.let {

                if(it.isNotEmpty()) {
                    mBinding.rvDishesList.visibility = View.VISIBLE
                    mBinding.tvNoDishesAddedYet.visibility = View.GONE

                    favDishAdapter.dishesList(it)
                } else {
                    mBinding.rvDishesList.visibility = View.GONE
                    mBinding.tvNoDishesAddedYet.visibility = View.VISIBLE
                }

               /* for(item in it) {
                    Log.i("Dish Title", "${item.id} :: ${item.title}")
                }*/
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_all_dishes, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.action_add_dish -> {
                startActivity(Intent(requireActivity(), AddUpdateDishActivity::class.java))
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }
}