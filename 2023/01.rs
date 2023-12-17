mod utils;
use utils::{read_input};

fn part_one(input: &Vec<String>) {
    let mut total = 0;

    for line in input {
        let digits: String = line.chars().filter(|&c| c.is_ascii_digit()).collect();
        let calibration_val_str: String = [
            digits.chars().nth(0).unwrap(),
            digits.chars().last().unwrap(),
        ]
        .iter()
        .collect();

        let calibration_val: i32 = (&calibration_val_str).parse().unwrap();

        total += calibration_val;
    }

    println!("part one: {}", total)
}

fn part_two(input: &Vec<String>) {
    let substitutes = [
        ["one", "o1ne"],
        ["two", "t2wo"],
        ["three", "thr3ee"],
        ["four", "f4our"],
        ["five", "fi5ve"],
        ["six", "s6ix"],
        ["seven", "sev7en"],
        ["eight", "eig8ht"],
        ["nine", "ni9ne"],
    ];

    let mut total = 0;

    for line in input {
        let mut target = String::from(line);

        for pair in substitutes {
            target = target.replace(&pair[0], &pair[1]);
        }

        let digits: String = target.chars().filter(|&c| c.is_ascii_digit()).collect();
        let calibration_val_str: String = [
            digits.chars().nth(0).unwrap(),
            digits.chars().last().unwrap(),
        ]
        .iter()
        .collect();

        let calibration_val: i32 = (&calibration_val_str).parse().unwrap();

        total += calibration_val;
    }

    println!("part two: {}", total)
}

fn main() {
    let file_path = "01.in";

    let input = read_input(file_path);

    part_one(&input);
    part_two(&input);
}
