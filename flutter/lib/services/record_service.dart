import 'package:dio/dio.dart';
import 'package:muscle_training/models/record.dart';
import 'package:muscle_training/utils/api_client.dart';

class RecordService {
  // 記録一覧を取得
  static Future<List<Record>> fetchRecords() async {
    try {
      final dio = await ApiClient.getDio();
      final response = await dio.get('/records');
      final data = response.data as List;
      return data.map((json) => Record.fromJson(json)).toList();
    } catch (e) {
      print('❌ 記録取得エラー: $e');
      return [];
    }
  }

  // 記録を追加
  static Future<bool> addRecord({
    required String exercise,
    required int weight,
    required int reps,
    required int sets,
  }) async {
    try {
      final dio = await ApiClient.getDio();
      final response = await dio.post('/records', data: {
        'date': DateTime.now().toIso8601String().split('T')[0],
        'exercise': exercise,
        'weight': weight,
        'reps': reps,
        'sets': sets,
      });
      print('✅ 記録追加成功: ${response.data}');
      return true;
    } catch (e) {
      print('❌ 記録追加失敗: $e');
      return false;
    }
  }

  // 記録を更新
  static Future<bool> updateRecord({
    required int id,
    required String exercise,
    required int weight,
    required int reps,
    required int sets,
  }) async {
    try {
      final dio = await ApiClient.getDio();
      final response = await dio.put('/records/$id', data: {
        'exercise': exercise,
        'weight': weight,
        'reps': reps,
        'sets': sets,
      });
      print('✅ 記録更新成功: ${response.data}');
      return true;
    } catch (e) {
      print('❌ 記録更新失敗: $e');
      return false;
    }
  }

  // 記録を削除
  static Future<bool> deleteRecord(int id) async {
    try {
      final dio = await ApiClient.getDio();
      final response = await dio.delete('/records/$id');
      print('🗑 記録削除成功: ${response.data}');
      return true;
    } catch (e) {
      print('❌ 記録削除失敗: $e');
      return false;
    }
  }
}
