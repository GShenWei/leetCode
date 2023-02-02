import CryptoJS from "crypto-js/crypto-js.js";

// xx();

function xx() {
    // 声明accessToken
    let appKey = 'deea26a0d08011ea83e7831773b4492d';
    let accessToken = '590073dc1227487287a344941ce408ff';
    let dt = 'pppooo';
    let pwd = CryptoJS.SHA1(dt).toString();
    console.log(pwd)
}

// yy();

function yy() {
    let appIsbn = "12"
    let timestamp = 123232323
    let nonce = "12"
    let series = "12"
    let appsecret = "12"
    let param = {
        "appIsbn": appIsbn,
        "timestamp": timestamp,
        "nonce": nonce,
        "series": series,
        "appsecret": appsecret,
    }
    let content = ""
    for (let key in param) {
        console.log(`${key}-${param[key]}`)
        let value = param[key]
        content = content + `${key}=${value}&`
    }
    if (content.endsWith("&")) {
        content = content.substring(0, content.length - 1);
    }
    console.log(content)
    let signature = CryptoJS.SHA1(content).toString()
    console.log(signature)

}

// zz();
function zz() {
    let dd = desEncrypt("AaBb123", 'dun7JsbJ')
    console.log(dd)
}

function desEncrypt(message, key) {
    const keyHex = CryptoJS.enc.Utf8.parse(key);
    const encrypted = CryptoJS.DES.encrypt(message, keyHex, {
        mode: CryptoJS.mode.ECB,
        padding: CryptoJS.pad.Pkcs7,
    });
    return encrypted.ciphertext.toString();
}

function desDecrypt(ciphertext, key) {
    const keyHex = CryptoJS.enc.Utf8.parse(key);
    const decrypted = CryptoJS.DES.decrypt({
        ciphertext: CryptoJS.enc.Hex.parse(ciphertext),
    }, keyHex, {
        mode: CryptoJS.mode.ECB,
        padding: CryptoJS.pad.Pkcs7,
    });
    return decrypted.toString(CryptoJS.enc.Utf8);
}

function DESEncrypt(data, key) {
    let result = ''
    try {
        key = key.length > 8 ? key.slice(0, 8) : key.concat('0'.repeat(8 - key.length))
        const keyHex = Buffer.from(key)
        const cipher = crypto.createCipheriv('des-ecb', keyHex, null)
        cipher.setAutoPadding(true)
        result = cipher.update(data, 'utf8', 'hex')
        result += cipher.final('hex')
    } catch (error) {
        logger.elog(`[cypher]des encrypt failed: ${error}, data:${data}, key:${key}`)
    }
    return result
}


function rep() {
    const path = "https://dengyue-1251316161.cos.ap-beijing.tencentcos.cn/avatars/1120852521243119616-1670393267.png"
    const r = /.*(\/avatars.*)/g;
    console.log(path.replace(r, "$1"))
}

describe('Array', function () {
    describe('#indexOf()', function () {
        it('should return -1 when the value is not present', function () {
            rep()
        });
    });
});






