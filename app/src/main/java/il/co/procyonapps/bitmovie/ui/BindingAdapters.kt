package il.co.procyonapps.bitmovie.ui

import android.util.Log
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.core.view.children
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import com.bumptech.glide.Glide
import il.co.procyonapps.bitmovie.R

@BindingAdapter("setImageUrl")
fun ImageView.setImage(url: String?) {
    if (url == null) return
    Glide.with(this)
        .load(url)
        .error(R.drawable.placeholder)
        .placeholder(R.drawable.placeholder)
        .centerCrop()
        .into(this)
}

@BindingAdapter("selectedRadioEnum")
fun RadioGroup.setSelectedRadioEnum(enum: Enum<*>?) {
    if (enum == null) return
    val checkedId = this.checkedRadioButtonId
    val enumOfFirst = children.firstOrNull { it.id == checkedId }?.getTag(R.id.filterSingleEnum) as? Enum<*>
    Log.d("RadioGroup binding", "setSelectedRadioEnum: enumOfFirst: $enumOfFirst, enum: $enum")
    
    if (enumOfFirst != enum) {
        val newIndex = children.indexOfFirst { it.getTag(R.id.filterSingleEnum) as? Enum<*> == enum }
        Log.d("RadioGroup binding", "setSelectedRadioEnum: $newIndex, enum: $enum")
        children.elementAtOrNull(newIndex)?.also {
            check(it.id)
        }
    }
}

@InverseBindingAdapter(attribute = "selectedRadioEnum")
fun RadioGroup.getSelectedRadioEnum(): Enum<*> {
    val checkedId = this.checkedRadioButtonId
    val enumOfFirst = children.first { it.id == checkedId }.getTag(R.id.filterSingleEnum) as Enum<*>
    Log.d("RadioGroup binding", "getSelectedRadioEnum: $enumOfFirst")
    return enumOfFirst
}

@BindingAdapter("selectedRadioEnumAttrChanged")
fun RadioGroup.setOnSelectedEnumListener(attrChange: InverseBindingListener) {
    setOnCheckedChangeListener { _, id ->
        Log.d("RadioGroup binding", "notify selection changed. id = ${context.resources.getResourceEntryName(id)}")
        attrChange.onChange()
    }
}

@BindingAdapter("filterEnumOption")
fun RadioButton.enumOption(option: Enum<*>) {
    Log.d("RadioGroup binding", "SelectedEnum set radio button option: $option")
    setTag(R.id.filterSingleEnum, option)
}