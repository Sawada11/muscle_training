import 'package:flutter/material.dart';
import 'package:dio/dio.dart';
import '../models/record.dart';
import '../services/api_client.dart';

class EditRecordScreen extends StatefulWidget {
  final Record record;

  const EditRecordScreen({Key? key, required this.record}) : super(key: key);

  @override
  _EditRecordScreenState createState() => _EditRecordScreenState();
}

class _EditRecordScreenState extends State<EditRecordScreen> {
  late TextEditingController exerciseController;
  late TextEditingController weightController;
  late TextEditingController repsController;
  late TextEditingController setsController;
  late TextEditingController memoController;

  @override
  void initState() {
    super.initState();
    exerciseController = TextEditingController(text: widget.record.exercise);
    weightController = TextEditingController(text: widget.record.weight.toString());
    repsController = TextEditingController(text: widget.record.reps.toString());
    setsController = TextEditingController(text: widget.record.sets.toString());
    memoController = TextEditingController(); // 任意の場合
  }

  Future<void> updateRecord() async {
    final Dio dio = await ApiClient.getDio();

    final updatedData = {
      "date": widget.record.date, // ✅ null を避けるため元の日付をそのまま送る
      "exercise": exerciseController.text,
      "weight": int.tryParse(weightController.text) ?? 0,
      "reps": int.tryParse(repsController.text) ?? 0,
      "sets": int.tryParse(setsController.text) ?? 0,
      "memo": memoController.text,
    };

    try {
      final response = await dio.put(
        "/api/records/${widget.record.id}",
        data: updatedData,
      );
      print("✅ 記録更新成功: ${response.data}");
      Navigator.pop(context, true); // 戻る際に成功を通知
    } catch (e) {
      print("❌ 記録更新失敗: $e");
    }
  }

  @override
  void dispose() {
    exerciseController.dispose();
    weightController.dispose();
    repsController.dispose();
    setsController.dispose();
    memoController.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text('記録を編集')),
      body: Padding(
        padding: const EdgeInsets.all(16.0),
        child: Column(
          children: [
            TextField(controller: exerciseController, decoration: const InputDecoration(labelText: '種目')),
            TextField(controller: weightController, decoration: const InputDecoration(labelText: '重量(kg)'), keyboardType: TextInputType.number),
            TextField(controller: repsController, decoration: const InputDecoration(labelText: '回数'), keyboardType: TextInputType.number),
            TextField(controller: setsController, decoration: const InputDecoration(labelText: 'セット数'), keyboardType: TextInputType.number),
            TextField(controller: memoController, decoration: const InputDecoration(labelText: 'メモ（任意）')),
            const SizedBox(height: 20),
            ElevatedButton(
              onPressed: updateRecord,
              child: const Text('更新する'),
            ),
          ],
        ),
      ),
    );
  }
}
