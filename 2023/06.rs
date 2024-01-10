use std::fs::read_to_string;

fn main() {
    let file_path = "06.in";

    let file_content = read_to_string(file_path).unwrap();

    part_one(&file_content);
    part_two(&file_content);
}

fn part_one(file_content: &String) {
    let mut races: Vec<Vec<i32>> = vec![];

    for line in file_content.trim().split("\n") {
        let words = &(line.split("   ").collect::<Vec<_>>())[1..];
        let words = words
            .iter()
            .filter_map(|&x| {
                if x == "" {
                    None
                } else {
                    Some(x.trim().parse::<i32>().unwrap())
                }
            })
            .collect::<Vec<_>>();
        races.push(words);
    }

    let races: Vec<(&i32, &i32)> = races[0].iter().zip(races[1].iter()).collect();

    let mut result = 1;

    for (time, distance) in races {
        for i in 0..=*time {
            if i * (*time - i) > *distance {
                result *= (*time + 1) - i * 2;
                break;
            }
        }
    }

    println!("part one: {result}");
}

fn part_two(file_content: &String) {
    let time: i64 = (file_content.trim().split("\n").collect::<Vec<_>>()[0])
        .chars()
        .filter(|&c| c.is_digit(10))
        .collect::<String>()
        .parse()
        .unwrap();
    let distance: i64 = (file_content.trim().split("\n").collect::<Vec<_>>()[1])
        .chars()
        .filter(|&c| c.is_digit(10))
        .collect::<String>()
        .parse()
        .unwrap();
    for i in 0..=time {
        if i * (time - i) > distance {
            println!("part two: {}", (time + 1) - i * 2);
            break;
        }
    }
}
