package com.textifly.divinekiddy.ui.Wishlist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.textifly.divinekiddy.R
import com.textifly.divinekiddy.databinding.FragmentWishlistBinding
import com.textifly.divinekiddy.ui.Wishlist.Adapter.WishlistAdapter
import com.textifly.divinekiddy.ui.Wishlist.Model.WishlistModel

class WishlistFragment : Fragment(),View.OnClickListener {
    private var _binding : FragmentWishlistBinding? = null
    private val binding get() = _binding!!
    lateinit var modelList : ArrayList<WishlistModel>

    override fun onResume() {
        super.onResume()
        try {
            var bottomNav : BottomNavigationView?  = activity?.findViewById(R.id.bottom_nav)
            bottomNav?.visibility = View.GONE
        }catch (e:Exception){}
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWishlistBinding.inflate(inflater,container,false)
        super.onResume()
        try {
            var bottomNav : BottomNavigationView?  = activity?.findViewById(R.id.bottom_nav)
            bottomNav?.visibility = View.GONE
        }catch (e:Exception){}
        btnClick()
        setLayout()
        loadWishlist()
        return binding.root
    }

    private fun setLayout() {
        binding.rvWishlist.layoutManager = GridLayoutManager(activity,2)
    }

    private fun loadWishlist() {
        modelList = ArrayList()
        val imageList = intArrayOf(
            R.drawable.c_girl, R.drawable.c_boys, R.drawable.c_newborn
        )

        val priceList = listOf("579","899","559")

        for(i in 0 until imageList.size){
            modelList.add(WishlistModel(imageList[i],priceList[i]))
        }

        binding.rvWishlist.adapter = WishlistAdapter(modelList)
    }

    private fun btnClick() {
        binding.llMenu.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when(view?.id){
            R.id.llMenu -> activity?.onBackPressed()
        }
    }

}