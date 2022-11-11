package com.example.veggie_table

import android.content.Context
import android.content.Intent
import android.graphics.Canvas
import android.net.Uri
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

//생성자로 listview adapter가 구현한 interface를 생성자로 받아 swipte한 대상을 전달한다
class PhoneBookListItemHelper(val con: Context): ItemTouchHelper.Callback() {
    var TAG = "PhoneBookListItemHelper"

    override fun isItemViewSwipeEnabled(): Boolean {
        return true
    }
    override fun isLongPressDragEnabled(): Boolean {
        return false
    }

    override fun onChildDraw(
        c: Canvas, recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean
    ) {
        Log.d(TAG, "Orign position : $dX,$dY")
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            val view = (viewHolder as PhoneBookListAdapter.ViewHolder).swipe_item_phone_book_list
            getDefaultUIUtil().onDraw(c, recyclerView, view, dX, dY, actionState, isCurrentlyActive)
            Log.d(TAG, "moved position : $dX,$dY")
        }
    }

    //상호작용 종료 및 애니메이션 종료 후 호출
    override fun clearView(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
    ) {
        getDefaultUIUtil().clearView((viewHolder as PhoneBookListAdapter.ViewHolder).swipe_item_phone_book_list)
    }

    //드래그 및 스와이프 방향을 제어. 드래그는 사용하지 않고, 양방향 스와이프를 사용한다.
    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
    ): Int {
        return makeMovementFlags(0, ItemTouchHelper.LEFT ) //or ItemTouchHelper.RIGHT
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder,
    ): Boolean {
        return false
    }

    //Swipe시 이벤트
    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val personViewHolder = viewHolder as PhoneBookListAdapter.ViewHolder
        val name = personViewHolder.tv_name_phone_book_list_item.text
        val phoneNumber = personViewHolder.tv_phone_number_phone_book_list_item.text
        Log.d("###", "swipe item's name : $name, phoneNumber : $phoneNumber")

        if(direction == ItemTouchHelper.LEFT){
            val callUriSwipedPerson = Uri.parse("tel:$phoneNumber")
            val callIntent = Intent(Intent.ACTION_DIAL, callUriSwipedPerson)
            con.startActivity(callIntent)

        } else {
            val smsUriSwipedPerson = Uri.parse("sms:$phoneNumber")
            val smsIntent = Intent(Intent.ACTION_SENDTO, smsUriSwipedPerson)
            con.startActivity(smsIntent)
        }
    }

}
