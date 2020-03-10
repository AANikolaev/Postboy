package anikolaev.postboy.view.interfaces

import anikolaev.postboy.view.models.Pairs

interface IClickHeadersPairModel {

    fun onItemHeadersClick(model: Pairs)

}

interface IClickParametersPairModel {

    fun onItemParametersClick(model: Pairs)

}