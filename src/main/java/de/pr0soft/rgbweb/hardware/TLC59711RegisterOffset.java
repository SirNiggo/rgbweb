package de.pr0soft.rgbweb.hardware;

public enum TLC59711RegisterOffset {
    B3(4, 2),
    G3(6, 2),
    R3(8, 2),
    B2(10, 2),
    G2(12, 2),
    R2(14, 2),
    B1(16, 2),
    G1(18, 2),
    R1(20, 2),
    B0(22, 2),
    G0(24, 2),
    R0(26, 2);

    private final int offset, length;

    TLC59711RegisterOffset(int offset, int length) {
        this.offset = offset;
        this.length = length;
    }

    public int getOffset() { return this.offset; }

    public int getLength() { return this.length; }
}
