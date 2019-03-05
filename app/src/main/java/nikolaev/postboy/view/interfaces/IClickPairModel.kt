package nikolaev.postboy.view.interfaces

import nikolaev.postboy.view.models.Pairs

interface IClickHeadersPairModel {

    fun onItemHeadersClick(model: Pairs)

}

interface IClickParametersPairModel {

    fun onItemParametersClick(model: Pairs)

}