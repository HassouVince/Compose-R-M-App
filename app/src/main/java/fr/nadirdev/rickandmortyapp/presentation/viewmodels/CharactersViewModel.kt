package fr.nadirdev.rickandmortyapp.presentation.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import fr.nadirdev.rickandmortyapp.domain.models.CharacterResult
import fr.nadirdev.rickandmortyapp.domain.usescases.GetCharacters
import fr.nadirdev.rickandmortyapp.domain.utils.Result

class CharactersViewModel(private val getCharacters: GetCharacters) : ViewModel() {

    private val _characters : MutableLiveData<Result<List<CharacterResult>>> = MutableLiveData()
    val characters :LiveData<Result<List<CharacterResult>>> get() = _characters

    val genders = listOf(ALL_GENDERS, MALE, FEMALE, GENDERLESS, UNKNOWN)
    val statusList = listOf(ALL_STATUS, ALIVE, DEAD, UNKNOWN)

    var genderState = mutableStateOf(genders[0])
    var statusState = mutableStateOf(statusList[0])

    fun getCharacters(isInternetAvailable: Boolean){
        _characters.postValue(Result.Loading)
        CoroutineScope(Dispatchers.Main).launch {
            getCharacters(
                gender = getGender(),
                status = getStatus(),
                isInternetAvailable = isInternetAvailable,
            ){
                _characters.postValue(it)
            }
        }
    }

    private fun getGender() =
        if(genderState.value == genders[0]) null else genderState.value

    private fun getStatus() =
        if(statusState.value == statusList[0]) null else statusState.value

    companion object{
        private const val ALL_GENDERS = "All genders"
        private const val MALE = "Male"
        private const val FEMALE = "Female"
        private const val GENDERLESS = "Genderless"
        private const val ALL_STATUS = "All status"
        private const val ALIVE = "Alive"
        private const val DEAD = "Dead"
        private const val UNKNOWN = "unknown"
    }
}