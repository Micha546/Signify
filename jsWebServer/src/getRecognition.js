const { StringDecoder } = require("string_decoder");
const decoder = new StringDecoder("utf8");

const sleep = (ms) => new Promise((r) => setTimeout(r, ms));

const getRecognition = async (img, child) => {
  const dataListener = (data) => {
    data = decoder.write(data);
    resData += data;
    console.log(`data after decode = ${data}`);
    if (data.slice(-1) === "\n") {
      finish = true;
      resData = resData.trimEnd();
    }
  };
  let resData = "";
  let finish = false;

  child.stdout.on("data", dataListener);
  child.stdin.write(img + "\n");

  while (!finish) {
    await sleep(20);
  }

  child.stdout.removeListener("data", dataListener);

  console.log(`resData = {${resData}}`);

  return resData;
};

module.exports = { getRecognition };
