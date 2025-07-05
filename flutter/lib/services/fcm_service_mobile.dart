import 'package:firebase_messaging/firebase_messaging.dart';
import 'package:shared_preferences/shared_preferences.dart';
import 'package:dio/dio.dart';

class FcmService {
  static Future<void> sendTokenToServer() async {
    final token = await FirebaseMessaging.instance.getToken();
    final prefs = await SharedPreferences.getInstance();
    final jwt = prefs.getString('jwt_token');

    if (token != null && jwt != null) {
      final dio = Dio();
      dio.options.headers['Authorization'] = 'Bearer $jwt';

      try {
        await dio.post(
          'http://192.168.0.64:8080/fcm/token',
          data: {'token': token},
        );
        print('✅ FCMトークン送信完了');
      } catch (e) {
        print('❌ FCMトークン送信失敗: $e');
      }
    } else {
      print('⚠️ JWTまたはFCMトークンが取得できませんでした');
    }
  }
}
