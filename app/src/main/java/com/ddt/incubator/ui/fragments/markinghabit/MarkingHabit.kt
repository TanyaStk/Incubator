package com.ddt.incubator.ui.fragments.markinghabit

import android.os.Build
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.ddt.incubator.R
import com.ddt.incubator.ui.viewmodels.HabitViewModel
import kotlinx.android.synthetic.main.fragment_marking_habit.*
import kotlinx.android.synthetic.main.toast_alligator.*
import kotlinx.android.synthetic.main.toast_duck.*
import kotlinx.android.synthetic.main.toast_green_dino.*
import kotlinx.android.synthetic.main.toast_penguin.*
import kotlinx.android.synthetic.main.toast_pink_dragon.*
import java.security.SecureRandom

class MarkingHabit : Fragment(R.layout.fragment_marking_habit) {

    private val args by navArgs<MarkingHabitArgs>()
    private lateinit var habitViewModel: HabitViewModel

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        habitViewModel = ViewModelProvider(this).get(HabitViewModel::class.java)

        //Retrieve data from our habit list
        tv_item_title_marking.text = args.selectedHabit.habit_title
        iv_habit_icon_marking.setImageResource(args.selectedHabit.imageId)
        tv_item_remainingDays.text = args.selectedHabit.remaining_days.toString()

        btn_confirm_success_marking.setOnClickListener {
            if (args.selectedHabit.remaining_days > 1 && chb_mark_success.isChecked) {
                updateHabit()
            } else {
                val myToast = Toast(context)
                myToast.duration = Toast.LENGTH_LONG
                myToast.setGravity(Gravity.CENTER_VERTICAL, 0, 0)
                when (rand()) {
                    1 -> {
                        val layout =
                            layoutInflater.inflate(
                                R.layout.toast_green_dino,
                                toast_green_dino_layout
                            )
                        myToast.view = layout//setting the view of custom toast layout
                    }
                    2 -> {
                        val layout = layoutInflater.inflate(R.layout.toast_duck, toast_duck_layout)
                        myToast.view = layout
                    }
                    3 -> {
                        val layout =
                            layoutInflater.inflate(R.layout.toast_alligator, toast_alligator_layout)
                        myToast.view = layout
                    }
                    4 -> {
                        val layout =
                            layoutInflater.inflate(R.layout.toast_penguin, toast_penguin_layout)
                        myToast.view = layout
                    }
                    5 -> {
                        val layout =
                            layoutInflater.inflate(
                                R.layout.toast_pink_dragon,
                                toast_pink_dragon_layout
                            )
                        myToast.view = layout
                    }
                }
                myToast.show()
                habitViewModel.deleteHabit(args.selectedHabit)
            }
            findNavController().navigate(R.id.action_markingHabit_to_habitList)
        }
    }

    private fun updateHabit() {
        args.selectedHabit.remaining_days -= 1
        habitViewModel.updateHabit(args.selectedHabit)
        tv_item_remainingDays.text = args.selectedHabit.remaining_days.toString()
    }

    private fun rand(): Int {
        val random = SecureRandom()
        random.setSeed(random.generateSeed(20))
        return random.nextInt(5) + 1
    }

    //Create options menu
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.edit_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_edit -> {
                val action =
                    MarkingHabitDirections.actionMarkingHabitToUpdateHabitItem2(args.selectedHabit)
                findNavController().navigate(action)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}