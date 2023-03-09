package br.com.sscode.wallpaperpexelapp.ui.fragment.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import br.com.sscode.wallpaperpexelapp.databinding.FragmentMainBinding
import br.com.sscode.wallpaperpexelapp.ui.fragment.category.CategoriesFragment
import br.com.sscode.wallpaperpexelapp.ui.fragment.collections.CollectionsFragment
import br.com.sscode.wallpaperpexelapp.ui.fragment.pageradapter.ViewPagerAdapter
import br.com.sscode.wallpaperpexelapp.ui.fragment.popular.PopularFragment
import com.google.android.material.tabs.TabLayoutMediator

class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    private val tabTitles = listOf("Popular", "Collections", "Categories")
    private val fragments = listOf(PopularFragment(), CollectionsFragment(), CategoriesFragment())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar()
        initViewPager()
        initTabLayout()
    }

    private fun initTabLayout() {
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = tabTitles[position]
        }.attach()
    }

    private fun initToolbar() {
        binding.toolbar.title = "Wallpapers"
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)
    }

    private fun initViewPager() {
        val pagerAdapter = ViewPagerAdapter(requireActivity(), fragments)
        binding.run {
            viewPager.adapter = pagerAdapter
        }
    }
}