interface CompressionStrategy {
    String compressFile(String fileName);
}

class Zip implements CompressionStrategy {
    @Override
    public String compressFile(String fileName) {
        return "File " + fileName + " compressed into zip format";
    }
}

class Gzip implements CompressionStrategy {
    @Override
    public String compressFile(String fileName) {
        return "File " + fileName + " compressed into Gzip format";
    }
}

class Bzip2 implements CompressionStrategy {
    @Override
    public String compressFile(String fileName) {
        return "File " + fileName + " compressed into Bzip2 format";
    }
}

class CompressFile {
    private CompressionStrategy compressionStrategy;

    public CompressFile(CompressionStrategy compressionStrategy) {
        this.compressionStrategy = compressionStrategy;
    }

    public String compress(String fileName) {
        return compressionStrategy.compressFile(fileName);
    }
}

public class StrategyDemo3 {
    public static void main(String[] args) {
        CompressFile compressZip = new CompressFile(new Zip());
        System.out.println(compressZip.compress("file1.txt"));

        CompressFile compressGzip = new CompressFile(new Gzip());
        System.out.println(compressGzip.compress("file2.txt"));

        CompressFile compressBzip2 = new CompressFile(new Bzip2());
        System.out.println(compressBzip2.compress("file3.txt"));
    }
}
