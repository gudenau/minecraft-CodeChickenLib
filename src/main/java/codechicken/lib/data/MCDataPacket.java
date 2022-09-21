package codechicken.lib.data;

import net.minecraft.network.FriendlyByteBuf;

//Why?
public final class MCDataPacket implements MCDataInput, MCDataOutput {
    private final FriendlyByteBuf buffer;

    public MCDataPacket(FriendlyByteBuf buffer) {
        this.buffer = buffer;
    }
    
    @Override
    public byte readByte() {
        return buffer.readByte();
    }
    
    @Override
    public short readUByte() {
        return buffer.readUnsignedByte();
    }
    
    @Override
    public char readChar() {
        return buffer.readChar();
    }
    
    @Override
    public short readShort() {
        return buffer.readShort();
    }
    
    @Override
    public int readUShort() {
        return buffer.readUnsignedShort();
    }
    
    @Override
    public int readInt() {
        return buffer.readInt();
    }
    
    @Override
    public long readLong() {
        return buffer.readLong();
    }
    
    @Override
    public float readFloat() {
        return buffer.readFloat();
    }
    
    @Override
    public double readDouble() {
        return buffer.readDouble();
    }
    
    @Override
    public boolean readBoolean() {
        return buffer.readBoolean();
    }
    
    @Override
    public MCDataOutput writeByte(int b) {
        buffer.writeByte(b);
        return this;
    }
    
    @Override
    public MCDataOutput writeChar(int c) {
        buffer.writeChar(c);
        return this;
    }
    
    @Override
    public MCDataOutput writeShort(int s) {
        buffer.writeShort(s);
        return this;
    }
    
    @Override
    public MCDataOutput writeInt(int i) {
        buffer.writeInt(i);
        return this;
    }
    
    @Override
    public MCDataOutput writeLong(long l) {
        buffer.writeLong(l);
        return this;
    }
    
    @Override
    public MCDataOutput writeFloat(float f) {
        buffer.writeFloat(f);
        return this;
    }
    
    @Override
    public MCDataOutput writeDouble(double d) {
        buffer.writeDouble(d);
        return this;
    }
    
    @Override
    public MCDataOutput writeBoolean(boolean b) {
        buffer.writeBoolean(b);
        return this;
    }
}
