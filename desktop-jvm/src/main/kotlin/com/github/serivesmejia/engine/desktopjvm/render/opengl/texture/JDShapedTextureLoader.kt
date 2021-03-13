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
        var width: Int
        var height: Int

        var id: Int

        MemoryStack.stackPush().use {
            val w = it.mallocInt(1)
            val h = it.mallocInt(1)
            val channels = it.mallocInt(1)

            val decoder = PNGDecoder(
                JDShapedTextureLoader::class.java.getResourceAsStream(resourcePath)
            )

            val buffer = ByteBuffer.allocateDirect(4 * decoder.width * decoder.height)
            decoder.decode(buffer, decoder.width * 4, PNGDecoder.Format.RGBA)

            buffer.flip()

            id = glGenTextures()
            width = w.get()
            height = h.get()

            glBindTexture(GL_TEXTURE_2D, id)
            glPixelStorei(GL_UNPACK_ALIGNMENT, 1)

            glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR.toFloat())
            glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR.toFloat())

            glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer)
            glGenerateMipmap(GL_TEXTURE_2D)

            glBindTexture(GL_TEXTURE_2D, 0)

            buffer.clear()
        }

        return JDShapedTexture(id, Size2(width.toFloat(), height.toFloat()))
    }

}