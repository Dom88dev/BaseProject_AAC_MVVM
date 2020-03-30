package com.project.base.ui.binding_adpters

import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide

/*
 뷰모델에 관찰가능한 boolean값을 할당해주어 양방향 데이터 바인딩 어댑터를 하려 했으나 리스너용 데이터바인딩어댑털터를 추가하여 단방향으로 함
 할당한 boolean 객체값이 변경되면 리프레쉬 로딩 애니메이션이 나타남(초기 네트워크 통신시 변경해주어 로딩 애니메이션 처리)
 */
@BindingAdapter("refreshing")
fun SwipeRefreshLayout.refreshing(visible: Boolean) {
    if (isRefreshing != visible) isRefreshing = visible
}

/*
 대신 onRefreshListener를 () -> Unit 타입으로 하여 지정해준다. 리프레싱 이벤트가 발생하면 지정한 () -> Unit 개체가 실행된다.
 */
@BindingAdapter("onRefresh")
fun SwipeRefreshLayout.OnRefreshListener(listener: SwipeRefreshLayout.OnRefreshListener) {
    setOnRefreshListener(listener)
}

/*
 이미지 리소스의 URI를 스트링으로 지정해주면 글라이드를 이용해 해당 이미지뷰에 이미지 리소스를 렌더링하도록 처리
 이미지뷰에 설정해놓은 scaleType에 따라 글라이드에서 scaleType이 적용되도록 처리해놓음.
 */
@BindingAdapter("imageUri")
fun loadImage(view: ImageView, uri: String?) {
    Log.e("BINDING_FUNCTION", "loadImage($uri)")
    if (uri != null) {
        when (view.scaleType) {
            ImageView.ScaleType.CENTER_CROP -> {
                Glide.with(view.context).load(uri).centerCrop().into(view)
            }
            ImageView.ScaleType.CENTER_INSIDE -> {
                Glide.with(view.context).load(uri).centerInside().into(view)
            }
            else -> {
                Glide.with(view.context).load(uri).fitCenter().into(view)
            }
        }
    }
}

