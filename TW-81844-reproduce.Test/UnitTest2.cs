namespace TW_81844_reproduce.Test {
    //dfgdfg
    [TestClass]
    public class UnitTest2 {
        [TestMethod]
        [DataRow(0, 1, 500)]
        [DataRow(0, 2, 400)]
        [DataRow(0, 3, 600)]
        [DataRow(0, 34, 400)]
        [DataRow(0, 35, 1000)]
        [DataRow(0, 36, 500)]
        [DataRow(0, 37, 500)]
        [DataRow(0, 38, 1000)]
        [DataRow(0, 39, 1000)]
        public async Task TestMethod21(int first, int second, int delay) {
            var sum = MathLocal.Sum(first, second);

            await Task.Delay(delay);
            Assert.AreEqual(first + second, sum);
        }

        [TestMethod]
        [DataRow(0, 4, 550)]
        [DataRow(0, 5, 500)]
        [DataRow(0, 6, 700)]
        [DataRow(0, 7, 500)]
        public async Task TestMethod22(int first, int second, int delay) {
            var sum = MathLocal.Sum(first, second);

            await Task.Delay(delay);
            Assert.AreEqual(first + second, sum);
        }

        [TestMethod]
        [DataRow(0, 22, 6000)]
        [DataRow(0, 23, 5500)]
        [DataRow(0, 24, 5000)]
        [DataRow(0, 25, 7000)]
        public async Task TestMethod23(int first, int second, int delay) {
            var sum = MathLocal.Sum(first, second);

            await Task.Delay(delay);
            Assert.AreEqual(first + second, sum);
        }

        [TestMethod]
        [DataRow(0, 30, 100)]
        [DataRow(0, 31, 500)]
        [DataRow(0, 32, 300)]
        [DataRow(0, 33, 400)]
        [DataRow(0, 34, 400)]
        [DataRow(0, 35, 1000)]
        public async Task TestMethod24(int first, int second, int delay) {
            var sum = MathLocal.Sum(first, second);

            await Task.Delay(delay);
            Assert.AreEqual(first + second, sum);
        }

        [TestMethod]
        [DataRow(0, 1, 500)]
        [DataRow(0, 2, 400)]
        public async Task TestMethod25(int first, int second, int delay) {
            var sum = MathLocal.Sum(first, second);

            await Task.Delay(delay);
            Assert.AreEqual(first + second, sum);
        }

        [TestMethod]
        [DataRow(0, 32, 300)]
        [DataRow(0, 33, 400)]
        [DataRow(0, 34, 400)]
        [DataRow(0, 35, 1000)]
        [DataRow(0, 36, 500)]
        [DataRow(0, 37, 500)]
        [DataRow(0, 38, 1000)]
        [DataRow(0, 39, 1000)]
        public async Task TestMethod26(int first, int second, int delay) {
            var sum = MathLocal.Sum(first, second);

            await Task.Delay(delay);
            Assert.AreEqual(first + second, sum);
        }

        [TestMethod]
        [DataRow(0, 29, 350)]
        [DataRow(0, 30, 100)]
        [DataRow(0, 31, 500)]
        [DataRow(0, 32, 300)]
        [DataRow(0, 33, 400)]
        public async Task TestMethod27(int first, int second, int delay) {
            var sum = MathLocal.Sum(first, second);

            await Task.Delay(delay);
            Assert.AreEqual(first + second, sum);
        }
    }
}
