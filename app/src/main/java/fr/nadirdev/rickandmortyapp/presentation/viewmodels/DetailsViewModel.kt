package fr.nadirdev.rickandmortyapp.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import fr.nadirdev.rickandmortyapp.domain.models.CharacterResult
import fr.nadirdev.rickandmortyapp.domain.usescases.GetCharacterDetails
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

import fr.nadirdev.rickandmortyapp.domain.utils.Result

class DetailsViewModel(private val getCharacterDetails: GetCharacterDetails) {

    private val _character : MutableLiveData<Result<CharacterResult>> = MutableLiveData()
    val character : LiveData<Result<CharacterResult>> get() = _character

    fun getCharacter(id : Int){
        _character.postValue(Result.Loading)
        CoroutineScope(Dispatchers.Main).launch {
            _character.postValue(getCharacterDetails(id))
        }
    }
}