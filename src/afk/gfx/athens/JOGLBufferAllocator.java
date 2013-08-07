package afk.gfx.athens;

import com.hackoeur.jglm.buffer.BufferAllocator;
import com.jogamp.common.nio.Buffers;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

/**
 *
 * @author daniel
 */
public class JOGLBufferAllocator implements BufferAllocator {
        @Override
        public ByteBuffer allocateByteBuffer(int sizeInBytes) {
                return Buffers.newDirectByteBuffer(sizeInBytes);
        }

        @Override
        public FloatBuffer allocateFloatBuffer(int sizeInFloats) {
                return Buffers.newDirectFloatBuffer(sizeInFloats);
        }
};
