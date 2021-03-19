package com.github.serivesmejia.engine.desktopjvm.render.opengl.texture

import com.github.serivesmejia.engine.common.math.geometry.Size2
import com.github.serivesmejia.engine.render.texture.ShapedTexture
import com.github.serivesmejia.engine.render.texture.ShapedTextureLoader
import de.matthiasmann.twl.utils.PNGDecoder
import org.lwjgl.opengl.GL30.*
import org.lwjgl.system.MemoryStack
import java.nio.ByteBuffer

object JDShapedTextureLoader : ShapedTextureLoader {

    override fun loadTexture(resourcePath: String): ShapedTexture {

        val decoder = PNGDecoder(
            JDShapedTextureLoader::class.java.getResourceAsStream(resourcePath)
        )

        val width = decoder.width
        val height = decoder.height

        val buffer = ByteBuffer.allocateDirect(4 * width * height)
        decoder.decode(buffer, width * 4, PNGDecoder.Format.RGBA)

        buffer.flip()

        val id = glGenTextures()

        println("$id, $width, $height")

        glBindTexture(GL_TEXTURE_2D, id)
        glPixelStorei(GL_UNPACK_ALIGNMENT, 1)

        glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR.toFloat())
        glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR.toFloat())

        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer)
        glGenerateMipmap(GL_TEXTURE_2D)

        glBindTexture(GL_TEXTURE_2D, 0)

        return JDShapedTexture(id, Size2(width.toFloat(), height.toFloat()))
    }

}