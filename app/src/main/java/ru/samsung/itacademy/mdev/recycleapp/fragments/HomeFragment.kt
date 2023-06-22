package ru.samsung.itacademy.mdev.recycleapp.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.widget.PopupMenu
import android.widget.ProgressBar
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.samsung.itacademy.mdev.recycleapp.R
import ru.samsung.itacademy.mdev.recycleapp.adapters.AdvicesAdapter
import ru.samsung.itacademy.mdev.recycleapp.viewmodels.HomeViewModel


class HomeFragment : Fragment() {

    private lateinit var recViewAdvices: RecyclerView
    private lateinit var factDescriptionTextView: TextView
    private lateinit var randomFactCard: CardView
    private lateinit var tvAdvices: TextView
    private lateinit var tv_facts: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var advicesAdapter: AdvicesAdapter
    private lateinit var imgInfo: ImageView

    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        recViewAdvices = view.findViewById(R.id.rec_view_advices)
        recViewAdvices.layoutManager = LinearLayoutManager(requireContext())
        recViewAdvices.setHasFixedSize(true)

        advicesAdapter = AdvicesAdapter()
        recViewAdvices.adapter = advicesAdapter

        factDescriptionTextView = view.findViewById(R.id.text_fact)
        randomFactCard = view.findViewById(R.id.random_fact_card)
        tv_facts = view.findViewById(R.id.tv_facts)
        tvAdvices = view.findViewById(R.id.tv_advices)
        progressBar = view.findViewById(R.id.progressBar)

        randomFactCard.visibility = View.GONE
        tv_facts.visibility = View.GONE
        tvAdvices.visibility = View.GONE
        recViewAdvices.visibility = View.GONE

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        imgInfo = view.findViewById(R.id.img_info)
        imgInfo.setOnClickListener {
            showPopupMenu()
        }

        homeViewModel.fetchAdvicesData()

        homeViewModel.advicesData.observe(viewLifecycleOwner) { advicesList ->
            advicesAdapter.setData(advicesList)
            progressBar.visibility = View.GONE
            tv_facts.visibility = View.VISIBLE
            tvAdvices.visibility = View.VISIBLE
            recViewAdvices.visibility = View.VISIBLE
        }

        homeViewModel.fetchRandomFact()

        homeViewModel.factDescription.observe(viewLifecycleOwner) { factDescription ->
            factDescriptionTextView.text = factDescription
            randomFactCard.visibility = View.VISIBLE
        }
    }

    private fun showPopupMenu() {
        val popupMenu = PopupMenu(requireContext(), imgInfo, Gravity.END, 0, R.style.CustomPopupMenu)
        popupMenu.menuInflater.inflate(R.menu.about_menu, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_about -> {
                    showAboutDialog()
                    true
                }
                else -> false
            }
        }
        popupMenu.show()
    }


    private fun showAboutDialog() {
        val dialogBuilder = AlertDialog.Builder(requireContext())
        val inflater = requireActivity().layoutInflater
        val dialogView = inflater.inflate(R.layout.dialog_about, null)
        dialogBuilder.setView(dialogView)
        dialogBuilder.setPositiveButton("Закрыть", null)
        val dialog = dialogBuilder.create()

        dialog.show()

        dialog.window?.setBackgroundDrawableResource(R.drawable.dialog_background)
    }




}
