class Record {
  final int id;
  final String date;
  final String exercise;
  final int weight;
  final int reps;
  final int sets;
  final String? memo; // ✅ ここが null を許容していること

  Record({
    required this.id,
    required this.date,
    required this.exercise,
    required this.weight,
    required this.reps,
    required this.sets,
    this.memo,
  });

  factory Record.fromJson(Map<String, dynamic> json) {
    return Record(
      id: json['id'],
      date: json['date'] ?? '', // ✅ 念のため fallback を追加しても良い
      exercise: json['exercise'] ?? '',
      weight: json['weight'] ?? 0,
      reps: json['reps'] ?? 0,
      sets: json['sets'] ?? 0,
      memo: json['memo'],
    );
  }
}
