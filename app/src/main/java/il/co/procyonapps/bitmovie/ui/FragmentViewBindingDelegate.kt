package il.co.procyonapps.bitmovie.ui

import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import androidx.viewbinding.ViewBinding
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

class FragmentViewBindingProperty<T : ViewBinding>(inline val binder: (View) -> T) : ReadOnlyProperty<Fragment, T?> {
    
    val TAG = this::class.simpleName ?: "Unspecified"
    private var viewBinding: T? = null
    private val lifecycleObserver = BindingLifecycleObserver()
    var ownerFrag = "N/A"
    override fun getValue(thisRef: Fragment, property: KProperty<*>): T? {
        
        thisRef::class.simpleName?.apply {
            ownerFrag = this
        }
        
        viewBinding?.let { return it }
    
        return try {
    
            val view = thisRef.requireView()
            thisRef.viewLifecycleOwner.lifecycle.addObserver(lifecycleObserver)
    
            val bind = binder(view)
            viewBinding = bind
            (bind as? ViewDataBinding)?.also {
                it.lifecycleOwner = thisRef.viewLifecycleOwner
            }
            bind
        } catch (e: IllegalStateException){
            Log.w(TAG, "getValue: trying to access view after view destruction", e)
            null
        }
    }
    
    private inner class BindingLifecycleObserver : LifecycleEventObserver {
        
        private val mainHandler = Handler(Looper.getMainLooper())
        
       
        override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
            if(event == Lifecycle.Event.ON_DESTROY){
                mainHandler.post {
                    Log.d(TAG, "onDestroy: nullify viewBinding for frag $ownerFrag")
                    viewBinding = null
                }
            }
        }
    }
}

inline fun <reified T : ViewBinding> Fragment.viewBinding(noinline binder: (View) -> T): ReadOnlyProperty<Fragment, T?> = FragmentViewBindingProperty<T>(binder)