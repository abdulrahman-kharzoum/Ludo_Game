import java.util.Arrays;

public class Board {
    public static int[] pathHuman = new int[57];
    public static int[] pathComputer = new int[57];
    public static int[] safeCells = new int[8];
    public final static char H = 'h', C = 'c';
    public boolean additionalThrow = false;
    static {
        //init human path
        pathHuman[0] = 201;
        pathHuman[1] = 186;
        pathHuman[2] = 171;
        pathHuman[3] = 156;
        pathHuman[4] = 141;
        pathHuman[5] = 125;
        pathHuman[6] = 124;
        pathHuman[7] = 123;
        pathHuman[8] = 122;
        pathHuman[9] = 121;
        pathHuman[10] = 120;
        pathHuman[11] = 105;
        pathHuman[12] = 90;
        pathHuman[13] = 91;
        pathHuman[14] = 92;
        pathHuman[15] = 93;
        pathHuman[16] = 94;
        pathHuman[17] = 95;
        pathHuman[18] = 81;
        pathHuman[19] = 66;
        pathHuman[20] = 51;
        pathHuman[21] = 36;
        pathHuman[22] = 21;
        pathHuman[23] = 6;
        pathHuman[24] = 7;
        pathHuman[25] = 8;
        pathHuman[26] = 23;
        pathHuman[27] = 38;
        pathHuman[28] = 53;
        pathHuman[29] = 68;
        pathHuman[30] = 83;
        pathHuman[31] = 99;
        pathHuman[32] = 100;
        pathHuman[33] = 101;
        pathHuman[34] = 102;
        pathHuman[35] = 103;
        pathHuman[36] = 104;
        pathHuman[37] = 119;
        pathHuman[38] = 134;
        pathHuman[39] = 133;
        pathHuman[40] = 132;
        pathHuman[41] = 131;
        pathHuman[42] = 130;
        pathHuman[43] = 129;
        pathHuman[44] = 143;
        pathHuman[45] = 158;
        pathHuman[46] = 173;
        pathHuman[47] = 188;
        pathHuman[48] = 203;
        pathHuman[49] = 218;
        pathHuman[50] = 217;
        pathHuman[51] = 202;
        pathHuman[52] = 187;
        pathHuman[53] = 172;
        pathHuman[54] = 157;
        pathHuman[55] = 142;
        pathHuman[56] = 127;


// Computer path
        pathComputer[0] = 23;
        pathComputer[1] = 38;
        pathComputer[2] = 53;
        pathComputer[3] = 68;
        pathComputer[4] = 83;
        pathComputer[5] = 99;
        pathComputer[6] = 100;
        pathComputer[7] = 101;
        pathComputer[8] = 102;
        pathComputer[9] = 103;
        pathComputer[10] = 104;
        pathComputer[11] = 119;
        pathComputer[12] = 134;
        pathComputer[13] = 133;
        pathComputer[14] = 132;
        pathComputer[15] = 131;
        pathComputer[16] = 130;
        pathComputer[17] = 129;
        pathComputer[18] = 143;
        pathComputer[19] = 158;
        pathComputer[20] = 173;
        pathComputer[21] = 188;
        pathComputer[22] = 203;
        pathComputer[23] = 218;
        pathComputer[24] = 217;
        pathComputer[25] = 216;
        pathComputer[26] = 201;
        pathComputer[27] = 186;
        pathComputer[28] = 171;
        pathComputer[29] = 156;
        pathComputer[30] = 141;
        pathComputer[31] = 125;
        pathComputer[32] = 124;
        pathComputer[33] = 123;
        pathComputer[34] = 122;
        pathComputer[35] = 121;
        pathComputer[36] = 120;
        pathComputer[37] = 105;
        pathComputer[38] = 90;
        pathComputer[39] = 91;
        pathComputer[40] = 92;
        pathComputer[41] = 93;
        pathComputer[42] = 94;
        pathComputer[43] = 95;
        pathComputer[44] = 81;
        pathComputer[45] = 66;
        pathComputer[46] = 51;
        pathComputer[47] = 36;
        pathComputer[48] = 21;
        pathComputer[49] = 6;
        pathComputer[50] = 7;
        pathComputer[51] = 22;
        pathComputer[52] = 37;
        pathComputer[53] = 52;
        pathComputer[54] = 67;
        pathComputer[55] = 82;
        pathComputer[56] = 97;


        //init safe cells
        safeCells[0] = 201;
        safeCells[1] = 188;
        safeCells[2] = 122;
        safeCells[3] = 91;
        safeCells[4] = 36;
        safeCells[5] = 23;
        safeCells[6] = 102;
        safeCells[7] = 133;

    }

    int[] piecesHuman = new int[4];
    int[] piecesComputer = new int[4];

    public Board() {
        piecesHuman[0] = -1;
        piecesHuman[1] = -1;
        piecesHuman[2] = -1;
        piecesHuman[3] = -1;
        piecesComputer[0] = -1;
        piecesComputer[1] = -1;
        piecesComputer[2] = -1;
        piecesComputer[3] = -1;
    }

    public Board(Board board) {
        this.piecesHuman = Arrays.copyOf(board.piecesHuman, 4);
        this.piecesComputer = Arrays.copyOf(board.piecesComputer, 4);
    }

    public boolean isSafe(int id) {
        for (int i = 0; i < safeCells.length; i++) {
            if (safeCells[i] == id) return true;
        }
        return false;
    }

}

///  For Two Players
    /*

                                |___________|
                                | 6   7   8 |
                                | 21  22  23|
                                |36  37  38 |
                                |51  52  53 |
                                |66  67  68 |
                                |81  82  83 |
       |------------------------|-----------|------------------------|
       | 90  91  92  93  94  95 |    97     |99 100 101 102 103 104  |
       |105                     |           |                     119|
       |120 121 122 123 124 125 |    127    |129 130 131 132 133 134 |
       |------------------------|-----------|------------------------|
                                |141 142 143|
                                |156 157 158|
                                |171 172 173|
                                |186 187 188|
                                |201 202 203|
                                |216 217 218|
                                |___________|
     */




    /*
                                |___________|
                                | 6   7   8 |
                                | 21  22  23|
                                |36  37  38 |
                                |51  52  53 |
                                |66  67  68 |
                                |81  82  83 |
       |------------------------|-----------|------------------------|
       | 90  91  92  93  94  95 |    97     |99 100 101 102 103 104  |
       |105 106 107 108 109 110 |           | 114 115 116 117 118 119|
       |120 121 122 123 124 125 |    127    |129 130 131 132 133 134 |
       |------------------------|-----------|------------------------|
                                |141 142 143|
                                |156 157 158|
                                |171 172 173|
                                |186 187 188|
                                |201 202 203|
                                |216 217 218|
                                |___________|
     */





    /*
                                |___________|
          0   1   2   3   4   5 | 6   7   8 | 9  10  11  12  13  14
         15  16  17  18  19  20 | 21  22  23|  24  25  26  27  28  29
         30  31  32  33  34  35 |36  37  38 | 39  40  41  42  43  44
         45  46  47  48  49  50 |51  52  53 | 54  55  56  57  58  59
         60  61  62  63  64  65 |66  67  68 | 69  70  71  72  73  74
         75  76  77  78  79  80 |81  82  83 | 84  85  86  87  88  89
       |------------------------|-----------|------------------------|
       | 90  91  92  93  94  95 |96  97  98 |99 100 101 102 103 104  |
       |105 106 107 108 109 110 |111 112 113| 114 115 116 117 118 119|
       |120 121 122 123 124 125 |126 127 128|129 130 131 132 133 134 |
       |------------------------|-----------|------------------------|
        135 136 137 138 139 140 |141 142 143| 144 145 146 147 148 149
        150 151 152 153 154 155 |156 157 158| 159 160 161 162 163 164
        165 166 167 168 169 170 |171 172 173| 174 175 176 177 178 179
        180 181 182 183 184 185 |186 187 188| 189 190 191 192 193 194
        195 196 197 198 199 200 |201 202 203| 204 205 206 207 208 209
        210 211 212 213 214 215 |216 217 218| 219 220 221 222 223 224
                                |___________|
*/






















