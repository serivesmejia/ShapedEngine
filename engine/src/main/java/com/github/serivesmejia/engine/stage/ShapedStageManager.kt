package com.github.serivesmejia.engine.stage

import com.github.serivesmejia.engine.common.ShapedComponent

class ShapedStageManager : ShapedComponent {

    lateinit var currentStage: ShapedStage
        private set

    override fun create(): ShapedStageManager {

        return this
    }

    override fun destroy(): ShapedStageManager {

        return this
    }

}