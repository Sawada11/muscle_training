import 'package:shared_preferences/shared_preferences.dart';

class TokenStorage {
  static const _key = 'jwt_token';

  // トークンを保存
  static Future<void> saveToken(String token) async {
    final prefs = await SharedPreferences.getInstance();
    await prefs.setString(_key, token);
  }

  // トークンを取得
  static Future<String?> getToken() async {
    final prefs = await SharedPreferences.getInstance();
    return prefs.getString(_key);
  }

  // トークンを削除（ログアウト用）
  static Future<void> removeToken() async {
    final prefs = await SharedPreferences.getInstance();
    await prefs.remove(_key);
  }
}
