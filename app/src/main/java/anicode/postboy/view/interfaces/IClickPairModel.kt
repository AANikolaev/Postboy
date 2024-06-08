package anicode.postboy.view.interfaces

import anicode.postboy.view.models.Pairs

interface IClickHeadersPairModel {

    fun onItemHeadersClick(model: Pairs)

}

interface IClickParametersPairModel {

    fun onItemParametersClick(model: Pairs)

}