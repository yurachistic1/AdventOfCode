use std::cmp::max;
use std::fs::File;
use std::io::{BufRead, BufReader};

fn read_input(file_path: &str) -> Vec<String> {
    let file = File::open(file_path).unwrap();
    let reader = BufReader::new(file);

    reader
        .lines()
        .filter_map(Result::ok)
        .map(|line| line.trim().to_string())
        .filter(|line| !line.is_empty())
        .collect()
}

fn part_one(input: &Vec<String>) {
    let (r, g, b) = (12, 13, 14);
    let mut total = 0;

    'outer: for (i, line) in input.iter().enumerate() {
        let x = line.split(": ").collect::<Vec<_>>()[1]
            .split("; ")
            .collect::<Vec<_>>();

        for group in x {
            let pairs: Vec<&str> = group.split(", ").collect();

            for pair in pairs {
                let (str_n, colour) = pair.split_once(" ").unwrap();
                let n: i32 = str_n.parse().unwrap();
                match colour {
                    "red" => {
                        if n > r {
                            continue 'outer;
                        }
                    }
                    "green" => {
                        if n > g {
                            continue 'outer;
                        }
                    }
                    "blue" => {
                        if n > b {
                            continue 'outer;
                        }
                    }
                    &_ => todo!(),
                }
            }
        }
        total += i + 1;
    }

    println!("part one: {}", total)
}

fn part_two(input: &Vec<String>) {
    let (mut r, mut g, mut b) = (0, 0, 0);
    let mut total = 0;

    for line in input {
        let x = line.split(": ").collect::<Vec<_>>()[1]
            .split("; ")
            .collect::<Vec<_>>();

        for group in x {
            let pairs: Vec<&str> = group.split(", ").collect();

            for pair in pairs {
                let (str_n, colour) = pair.split_once(" ").unwrap();
                let n: i32 = str_n.parse().unwrap();
                match colour {
                    "red" => {
                        r = max(r, n);
                    }
                    "green" => {
                        g = max(g, n);
                    }
                    "blue" => {
                        b = max(b, n);
                    }
                    &_ => todo!(),
                }
            }
        }
        total += r * g * b;
        (r, g, b) = (0, 0, 0)
    }

    println!("part two: {}", total)
}

fn main() {
    let file_path = "02.in";

    let input = read_input(file_path);

    part_one(&input);
    part_two(&input);
}
