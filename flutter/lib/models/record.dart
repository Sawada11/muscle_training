class Record {
  final int id;
  final String date;
  final String exercise;
  final int weight;
  final int reps;
  final int sets;

  Record({
    required this.id,
    required this.date,
    required this.exercise,
    required this.weight,
    required this.reps,
    required this.sets,
  });

  factory Record.fromJson(Map<String, dynamic> json) {
    return Record(
      id: json['id'],
      date: json['date'],
      exercise: json['exercise'],
      weight: json['weight'],
      reps: json['reps'],
      sets: json['sets'],
    );
  }
}
