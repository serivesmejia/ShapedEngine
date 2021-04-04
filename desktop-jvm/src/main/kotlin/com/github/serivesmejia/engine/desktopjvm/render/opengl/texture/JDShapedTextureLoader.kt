package com.github.serivesmejia.engine.desktopjvm.render.opengl.texture

import com.github.serivesmejia.engine.common.math.geometry.Size2
import com.github.serivesmejia.engine.desktopjvm.util.IOUtil
import com.github.serivesmejia.engine.render.texture.ShapedTexture
import com.github.serivesmejia.engine.render.texture.ShapedTextureLoader
import org.lwjgl.opengl.GL30.*
import org.lwjgl.stb.STBImage.*
import org.lwjgl.system.MemoryStack
import java.nio.ByteBuffer
import kotlin.experimental.and
import kotlin.math.roundToInt


object JDShapedTextureLoader : ShapedTextureLoader {

    override fun loadTexture(resourcePath: String): ShapedTexture {

        val imageBuffer = IOUtil.ioResourceToByteBuffer(resourcePath, 8 * 1024)

        var width = 0
        var height = 0

        val id = glGenTextures()

        MemoryStack.stackPush().use {
            val w = it.mallocInt(1)
            val h = it.mallocInt(1)
            val c = it.mallocInt(1)

            val image = stbi_load_from_memory(imageBuffer, w, h, c, 0)
                ?: throw RuntimeException("Failed to load image: " + stbi_failure_reason())

            width = w.get()
            height = h.get()

            val comp = c.get()

            glBindTexture(GL_TEXTURE_2D, id)

            val format = if (comp == 3) {
                if (width and 3 != 0) {
                    glPixelStorei(GL_UNPACK_ALIGNMENT, 2 - (width and 1))
                }
                GL_RGB
            } else {
                premultiplyAlpha(image, width, height)
                GL_RGBA
            }

            println(format == GL_RGB)

            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);

            glTexImage2D(GL_TEXTURE_2D, 0, format, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, image)
            glGenerateMipmap(GL_TEXTURE_2D)

            stbi_image_free(image)
        }

        return JDShapedTexture(id, Size2(width.toFloat(), height.toFloat()))
    }

    private fun premultiplyAlpha(image: ByteBuffer, w: Int, h: Int) {

        val stride: Int = w * 4

        for (y in 0 until h) {
            for (x in 0 until w) {
                val i = y * stride + x * 4
                val alpha: Float = (image.get(i + 3) and 0xFF.toByte()) / 255.0f
                val FF = 0xFF.toByte()

                image.put(i + 0, ((image.get(i + 0) and FF) * alpha).roundToInt().toByte())
                image.put(i + 1, ((image.get(i + 1) and FF) * alpha).roundToInt().toByte())
                image.put(i + 2, ((image.get(i + 2) and FF) * alpha).roundToInt().toByte())
            }
        }
    }

}